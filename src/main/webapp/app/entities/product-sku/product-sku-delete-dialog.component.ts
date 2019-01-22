import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSku } from 'app/shared/model/product-sku.model';
import { ProductSkuService } from './product-sku.service';

@Component({
    selector: 'jhi-product-sku-delete-dialog',
    templateUrl: './product-sku-delete-dialog.component.html'
})
export class ProductSkuDeleteDialogComponent {
    productSku: IProductSku;

    constructor(
        protected productSkuService: ProductSkuService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productSkuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productSkuListModification',
                content: 'Deleted an productSku'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-sku-delete-popup',
    template: ''
})
export class ProductSkuDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productSku }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductSkuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.productSku = productSku;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
