import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMemberOrderItem } from 'app/shared/model/member-order-item.model';
import { MemberOrderItemService } from './member-order-item.service';
import { IMemberOrder } from 'app/shared/model/member-order.model';
import { MemberOrderService } from 'app/entities/member-order';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-member-order-item-update',
    templateUrl: './member-order-item-update.component.html'
})
export class MemberOrderItemUpdateComponent implements OnInit {
    memberOrderItem: IMemberOrderItem;
    isSaving: boolean;

    memberorders: IMemberOrder[];

    products: IProduct[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected memberOrderItemService: MemberOrderItemService,
        protected memberOrderService: MemberOrderService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ memberOrderItem }) => {
            this.memberOrderItem = memberOrderItem;
        });
        this.memberOrderService.query().subscribe(
            (res: HttpResponse<IMemberOrder[]>) => {
                this.memberorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.memberOrderItem.id !== undefined) {
            this.subscribeToSaveResponse(this.memberOrderItemService.update(this.memberOrderItem));
        } else {
            this.subscribeToSaveResponse(this.memberOrderItemService.create(this.memberOrderItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberOrderItem>>) {
        result.subscribe((res: HttpResponse<IMemberOrderItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMemberOrderById(index: number, item: IMemberOrder) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
