import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSku } from 'app/shared/model/product-sku.model';

@Component({
    selector: 'jhi-product-sku-detail',
    templateUrl: './product-sku-detail.component.html'
})
export class ProductSkuDetailComponent implements OnInit {
    productSku: IProductSku;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productSku }) => {
            this.productSku = productSku;
        });
    }

    previousState() {
        window.history.back();
    }
}
