import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProductSku } from 'app/shared/model/product-sku.model';
import { ProductSkuService } from './product-sku.service';
import { ITcaOrder } from 'app/shared/model/tca-order.model';
import { TcaOrderService } from 'app/entities/tca-order';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-product-sku-update',
    templateUrl: './product-sku-update.component.html'
})
export class ProductSkuUpdateComponent implements OnInit {
    productSku: IProductSku;
    isSaving: boolean;

    tcaorders: ITcaOrder[];

    products: IProduct[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productSkuService: ProductSkuService,
        protected tcaOrderService: TcaOrderService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productSku }) => {
            this.productSku = productSku;
        });
        this.tcaOrderService.query().subscribe(
            (res: HttpResponse<ITcaOrder[]>) => {
                this.tcaorders = res.body;
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
        if (this.productSku.id !== undefined) {
            this.subscribeToSaveResponse(this.productSkuService.update(this.productSku));
        } else {
            this.subscribeToSaveResponse(this.productSkuService.create(this.productSku));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSku>>) {
        result.subscribe((res: HttpResponse<IProductSku>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTcaOrderById(index: number, item: ITcaOrder) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
