import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductAsset } from 'app/shared/model/product-asset.model';

@Component({
    selector: 'jhi-product-asset-detail',
    templateUrl: './product-asset-detail.component.html'
})
export class ProductAssetDetailComponent implements OnInit {
    productAsset: IProductAsset;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productAsset }) => {
            this.productAsset = productAsset;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
