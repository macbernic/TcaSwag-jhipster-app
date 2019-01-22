import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TcaOrder } from 'app/shared/model/tca-order.model';
import { TcaOrderService } from './tca-order.service';
import { TcaOrderComponent } from './tca-order.component';
import { TcaOrderDetailComponent } from './tca-order-detail.component';
import { TcaOrderUpdateComponent } from './tca-order-update.component';
import { TcaOrderDeletePopupComponent } from './tca-order-delete-dialog.component';
import { ITcaOrder } from 'app/shared/model/tca-order.model';

@Injectable({ providedIn: 'root' })
export class TcaOrderResolve implements Resolve<ITcaOrder> {
    constructor(private service: TcaOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TcaOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TcaOrder>) => response.ok),
                map((tcaOrder: HttpResponse<TcaOrder>) => tcaOrder.body)
            );
        }
        return of(new TcaOrder());
    }
}

export const tcaOrderRoute: Routes = [
    {
        path: 'tca-order',
        component: TcaOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-order/:id/view',
        component: TcaOrderDetailComponent,
        resolve: {
            tcaOrder: TcaOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-order/new',
        component: TcaOrderUpdateComponent,
        resolve: {
            tcaOrder: TcaOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-order/:id/edit',
        component: TcaOrderUpdateComponent,
        resolve: {
            tcaOrder: TcaOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tcaOrderPopupRoute: Routes = [
    {
        path: 'tca-order/:id/delete',
        component: TcaOrderDeletePopupComponent,
        resolve: {
            tcaOrder: TcaOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
