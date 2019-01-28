import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductSku } from 'app/shared/model/product-sku.model';
import { AccountService } from 'app/core';
import { ProductSkuService } from './product-sku.service';

@Component({
    selector: 'jhi-product-sku',
    templateUrl: './product-sku.component.html'
})
export class ProductSkuComponent implements OnInit, OnDestroy {
    productSkus: IProductSku[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productSkuService: ProductSkuService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productSkuService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductSku[]>) => res.ok),
                map((res: HttpResponse<IProductSku[]>) => res.body)
            )
            .subscribe(
                (res: IProductSku[]) => {
                    this.productSkus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductSkus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductSku) {
        return item.id;
    }

    registerChangeInProductSkus() {
        this.eventSubscriber = this.eventManager.subscribe('productSkuListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
