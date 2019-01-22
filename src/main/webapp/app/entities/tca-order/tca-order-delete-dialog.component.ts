import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcaOrder } from 'app/shared/model/tca-order.model';
import { TcaOrderService } from './tca-order.service';

@Component({
    selector: 'jhi-tca-order-delete-dialog',
    templateUrl: './tca-order-delete-dialog.component.html'
})
export class TcaOrderDeleteDialogComponent {
    tcaOrder: ITcaOrder;

    constructor(protected tcaOrderService: TcaOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tcaOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tcaOrderListModification',
                content: 'Deleted an tcaOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tca-order-delete-popup',
    template: ''
})
export class TcaOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tcaOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TcaOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tcaOrder = tcaOrder;
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
