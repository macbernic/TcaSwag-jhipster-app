import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TcaSwagSharedModule } from 'app/shared';
import {
    MemberOrderComponent,
    MemberOrderDetailComponent,
    MemberOrderUpdateComponent,
    MemberOrderDeletePopupComponent,
    MemberOrderDeleteDialogComponent,
    memberOrderRoute,
    memberOrderPopupRoute
} from './';

const ENTITY_STATES = [...memberOrderRoute, ...memberOrderPopupRoute];

@NgModule({
    imports: [TcaSwagSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MemberOrderComponent,
        MemberOrderDetailComponent,
        MemberOrderUpdateComponent,
        MemberOrderDeleteDialogComponent,
        MemberOrderDeletePopupComponent
    ],
    entryComponents: [MemberOrderComponent, MemberOrderUpdateComponent, MemberOrderDeleteDialogComponent, MemberOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagMemberOrderModule {}
