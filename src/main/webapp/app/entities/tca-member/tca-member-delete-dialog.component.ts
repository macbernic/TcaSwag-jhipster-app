import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcaMember } from 'app/shared/model/tca-member.model';
import { TcaMemberService } from './tca-member.service';

@Component({
    selector: 'jhi-tca-member-delete-dialog',
    templateUrl: './tca-member-delete-dialog.component.html'
})
export class TcaMemberDeleteDialogComponent {
    tcaMember: ITcaMember;

    constructor(
        protected tcaMemberService: TcaMemberService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tcaMemberService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tcaMemberListModification',
                content: 'Deleted an tcaMember'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tca-member-delete-popup',
    template: ''
})
export class TcaMemberDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tcaMember }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TcaMemberDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tcaMember = tcaMember;
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
