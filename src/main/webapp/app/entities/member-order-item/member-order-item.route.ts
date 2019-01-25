import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MemberOrderItem } from 'app/shared/model/member-order-item.model';
import { MemberOrderItemService } from './member-order-item.service';
import { MemberOrderItemComponent } from './member-order-item.component';
import { MemberOrderItemDetailComponent } from './member-order-item-detail.component';
import { MemberOrderItemUpdateComponent } from './member-order-item-update.component';
import { MemberOrderItemDeletePopupComponent } from './member-order-item-delete-dialog.component';
import { IMemberOrderItem } from 'app/shared/model/member-order-item.model';

@Injectable({ providedIn: 'root' })
export class MemberOrderItemResolve implements Resolve<IMemberOrderItem> {
    constructor(private service: MemberOrderItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MemberOrderItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MemberOrderItem>) => response.ok),
                map((memberOrderItem: HttpResponse<MemberOrderItem>) => memberOrderItem.body)
            );
        }
        return of(new MemberOrderItem());
    }
}

export const memberOrderItemRoute: Routes = [
    {
        path: 'member-order-item',
        component: MemberOrderItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order-item/:id/view',
        component: MemberOrderItemDetailComponent,
        resolve: {
            memberOrderItem: MemberOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order-item/new',
        component: MemberOrderItemUpdateComponent,
        resolve: {
            memberOrderItem: MemberOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'member-order-item/:id/edit',
        component: MemberOrderItemUpdateComponent,
        resolve: {
            memberOrderItem: MemberOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrderItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const memberOrderItemPopupRoute: Routes = [
    {
        path: 'member-order-item/:id/delete',
        component: MemberOrderItemDeletePopupComponent,
        resolve: {
            memberOrderItem: MemberOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MemberOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
