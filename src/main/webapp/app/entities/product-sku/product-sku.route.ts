import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductSku } from 'app/shared/model/product-sku.model';
import { ProductSkuService } from './product-sku.service';
import { ProductSkuComponent } from './product-sku.component';
import { ProductSkuDetailComponent } from './product-sku-detail.component';
import { ProductSkuUpdateComponent } from './product-sku-update.component';
import { ProductSkuDeletePopupComponent } from './product-sku-delete-dialog.component';
import { IProductSku } from 'app/shared/model/product-sku.model';

@Injectable({ providedIn: 'root' })
export class ProductSkuResolve implements Resolve<IProductSku> {
    constructor(private service: ProductSkuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductSku> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductSku>) => response.ok),
                map((productSku: HttpResponse<ProductSku>) => productSku.body)
            );
        }
        return of(new ProductSku());
    }
}

export const productSkuRoute: Routes = [
    {
        path: 'product-sku',
        component: ProductSkuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductSkus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-sku/:id/view',
        component: ProductSkuDetailComponent,
        resolve: {
            productSku: ProductSkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductSkus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-sku/new',
        component: ProductSkuUpdateComponent,
        resolve: {
            productSku: ProductSkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductSkus'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-sku/:id/edit',
        component: ProductSkuUpdateComponent,
        resolve: {
            productSku: ProductSkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductSkus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productSkuPopupRoute: Routes = [
    {
        path: 'product-sku/:id/delete',
        component: ProductSkuDeletePopupComponent,
        resolve: {
            productSku: ProductSkuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductSkus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
