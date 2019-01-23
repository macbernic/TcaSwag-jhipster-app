import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMemberOrder } from 'app/shared/model/member-order.model';
import { MemberOrderService } from './member-order.service';

@Component({
    selector: 'jhi-member-order-delete-dialog',
    templateUrl: './member-order-delete-dialog.component.html'
})
export class MemberOrderDeleteDialogComponent {
    memberOrder: IMemberOrder;

    constructor(
        protected memberOrderService: MemberOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.memberOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'memberOrderListModification',
                content: 'Deleted an memberOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-member-order-delete-popup',
    template: ''
})
export class MemberOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ memberOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MemberOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.memberOrder = memberOrder;
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
