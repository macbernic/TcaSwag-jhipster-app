import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMemberOrderItem } from 'app/shared/model/member-order-item.model';
import { MemberOrderItemService } from './member-order-item.service';

@Component({
    selector: 'jhi-member-order-item-delete-dialog',
    templateUrl: './member-order-item-delete-dialog.component.html'
})
export class MemberOrderItemDeleteDialogComponent {
    memberOrderItem: IMemberOrderItem;

    constructor(
        protected memberOrderItemService: MemberOrderItemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.memberOrderItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'memberOrderItemListModification',
                content: 'Deleted an memberOrderItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-member-order-item-delete-popup',
    template: ''
})
export class MemberOrderItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ memberOrderItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MemberOrderItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.memberOrderItem = memberOrderItem;
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
