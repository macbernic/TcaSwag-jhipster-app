import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITcaMember } from 'app/shared/model/tca-member.model';
import { TcaMemberService } from './tca-member.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-tca-member-update',
    templateUrl: './tca-member-update.component.html'
})
export class TcaMemberUpdateComponent implements OnInit {
    tcaMember: ITcaMember;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tcaMemberService: TcaMemberService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tcaMember }) => {
            this.tcaMember = tcaMember;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tcaMember.id !== undefined) {
            this.subscribeToSaveResponse(this.tcaMemberService.update(this.tcaMember));
        } else {
            this.subscribeToSaveResponse(this.tcaMemberService.create(this.tcaMember));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcaMember>>) {
        result.subscribe((res: HttpResponse<ITcaMember>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
