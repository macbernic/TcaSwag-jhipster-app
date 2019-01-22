import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITcaOrder } from 'app/shared/model/tca-order.model';
import { TcaOrderService } from './tca-order.service';
import { ITcaMember } from 'app/shared/model/tca-member.model';
import { TcaMemberService } from 'app/entities/tca-member';

@Component({
    selector: 'jhi-tca-order-update',
    templateUrl: './tca-order-update.component.html'
})
export class TcaOrderUpdateComponent implements OnInit {
    tcaOrder: ITcaOrder;
    isSaving: boolean;

    tcamembers: ITcaMember[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tcaOrderService: TcaOrderService,
        protected tcaMemberService: TcaMemberService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tcaOrder }) => {
            this.tcaOrder = tcaOrder;
            this.date = this.tcaOrder.date != null ? this.tcaOrder.date.format(DATE_TIME_FORMAT) : null;
        });
        this.tcaMemberService.query().subscribe(
            (res: HttpResponse<ITcaMember[]>) => {
                this.tcamembers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.tcaOrder.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.tcaOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.tcaOrderService.update(this.tcaOrder));
        } else {
            this.subscribeToSaveResponse(this.tcaOrderService.create(this.tcaOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcaOrder>>) {
        result.subscribe((res: HttpResponse<ITcaOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTcaMemberById(index: number, item: ITcaMember) {
        return item.id;
    }
}
