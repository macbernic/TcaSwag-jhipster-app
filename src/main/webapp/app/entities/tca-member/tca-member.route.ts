import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TcaMember } from 'app/shared/model/tca-member.model';
import { TcaMemberService } from './tca-member.service';
import { TcaMemberComponent } from './tca-member.component';
import { TcaMemberDetailComponent } from './tca-member-detail.component';
import { TcaMemberUpdateComponent } from './tca-member-update.component';
import { TcaMemberDeletePopupComponent } from './tca-member-delete-dialog.component';
import { ITcaMember } from 'app/shared/model/tca-member.model';

@Injectable({ providedIn: 'root' })
export class TcaMemberResolve implements Resolve<ITcaMember> {
    constructor(private service: TcaMemberService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TcaMember> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TcaMember>) => response.ok),
                map((tcaMember: HttpResponse<TcaMember>) => tcaMember.body)
            );
        }
        return of(new TcaMember());
    }
}

export const tcaMemberRoute: Routes = [
    {
        path: 'tca-member',
        component: TcaMemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaMembers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-member/:id/view',
        component: TcaMemberDetailComponent,
        resolve: {
            tcaMember: TcaMemberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaMembers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-member/new',
        component: TcaMemberUpdateComponent,
        resolve: {
            tcaMember: TcaMemberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaMembers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tca-member/:id/edit',
        component: TcaMemberUpdateComponent,
        resolve: {
            tcaMember: TcaMemberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaMembers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tcaMemberPopupRoute: Routes = [
    {
        path: 'tca-member/:id/delete',
        component: TcaMemberDeletePopupComponent,
        resolve: {
            tcaMember: TcaMemberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TcaMembers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
