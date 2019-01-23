import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MemberOrder } from 'app/shared/model/member-order.model';
import { MemberOrderService } from './member-order.service';
import { MemberOrderComponent } from './member-order.component';
import { MemberOrderDetailComponent } from './member-order-detail.component';
import { MemberOrderUpdateComponent } from './member-order-update.component';
import { MemberOrderDeletePopupComponent } from './member-order-delete-dialog.component';
import { IMemberOrder } from 'app/shared/model/member-order.model';

@Injectable({ providedIn: 'root' })
export class MemberOrderResolve implements Resolve<IMemberOrder> {
    constructor(private service: MemberOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MemberOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MemberOrder>) => response.ok),
                map((memberOrder: HttpResponse<MemberOrder>) => memberOrder.body)
            );
        }
        return of(new MemberOrder());
    }
}

export const memberOrderRoute: Routes = [
    {
        path: 'member-order',
        component: MemberOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order/:id/view',
        component: MemberOrderDetailComponent,
        resolve: {
            memberOrder: MemberOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order/new',
        component: MemberOrderUpdateComponent,
        resolve: {
            memberOrder: MemberOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order/:id/edit',
        component: MemberOrderUpdateComponent,
        resolve: {
            memberOrder: MemberOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const memberOrderPopupRoute: Routes = [
    {
        path: 'member-order/:id/delete',
        component: MemberOrderDeletePopupComponent,
        resolve: {
            memberOrder: MemberOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
