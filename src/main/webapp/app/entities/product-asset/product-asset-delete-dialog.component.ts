import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAsset } from 'app/shared/model/product-asset.model';
import { ProductAssetService } from './product-asset.service';

@Component({
    selector: 'jhi-product-asset-delete-dialog',
    templateUrl: './product-asset-delete-dialog.component.html'
})
export class ProductAssetDeleteDialogComponent {
    productAsset: IProductAsset;

    constructor(
        protected productAssetService: ProductAssetService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productAssetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productAssetListModification',
                content: 'Deleted an productAsset'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-asset-delete-popup',
    template: ''
})
export class ProductAssetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productAsset }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductAssetDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productAsset = productAsset;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-asset', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-asset', { outlets: { popup: null } }]);
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
