import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductAsset } from 'app/shared/model/product-asset.model';
import { ProductAssetService } from './product-asset.service';
import { ProductAssetComponent } from './product-asset.component';
import { ProductAssetDetailComponent } from './product-asset-detail.component';
import { ProductAssetUpdateComponent } from './product-asset-update.component';
import { ProductAssetDeletePopupComponent } from './product-asset-delete-dialog.component';
import { IProductAsset } from 'app/shared/model/product-asset.model';

@Injectable({ providedIn: 'root' })
export class ProductAssetResolve implements Resolve<IProductAsset> {
    constructor(private service: ProductAssetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductAsset> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductAsset>) => response.ok),
                map((productAsset: HttpResponse<ProductAsset>) => productAsset.body)
            );
        }
        return of(new ProductAsset());
    }
}

export const productAssetRoute: Routes = [
    {
        path: '',
        component: ProductAssetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductAssetDetailComponent,
        resolve: {
            productAsset: ProductAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductAssetUpdateComponent,
        resolve: {
            productAsset: ProductAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductAssetUpdateComponent,
        resolve: {
            productAsset: ProductAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductAssets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productAssetPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductAssetDeletePopupComponent,
        resolve: {
            productAsset: ProductAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductAssets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
