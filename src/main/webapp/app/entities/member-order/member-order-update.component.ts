import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMemberOrder } from 'app/shared/model/member-order.model';
import { MemberOrderService } from './member-order.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member';

@Component({
    selector: 'jhi-member-order-update',
    templateUrl: './member-order-update.component.html'
})
export class MemberOrderUpdateComponent implements OnInit {
    memberOrder: IMemberOrder;
    isSaving: boolean;

    members: IMember[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected memberOrderService: MemberOrderService,
        protected memberService: MemberService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ memberOrder }) => {
            this.memberOrder = memberOrder;
        });
        this.memberService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMember[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMember[]>) => response.body)
            )
            .subscribe((res: IMember[]) => (this.members = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.memberOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.memberOrderService.update(this.memberOrder));
        } else {
            this.subscribeToSaveResponse(this.memberOrderService.create(this.memberOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberOrder>>) {
        result.subscribe((res: HttpResponse<IMemberOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMemberById(index: number, item: IMember) {
        return item.id;
    }
}
