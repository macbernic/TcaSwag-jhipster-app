import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductSku } from 'app/shared/model/product-sku.model';
import { ProductSkuService } from './product-sku.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-product-sku-update',
    templateUrl: './product-sku-update.component.html'
})
export class ProductSkuUpdateComponent implements OnInit {
    productSku: IProductSku;
    isSaving: boolean;

    products: IProduct[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productSkuService: ProductSkuService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productSku }) => {
            this.productSku = productSku;
        });
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
