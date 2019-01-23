import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TcaSwagSharedModule } from 'app/shared';
import {
    MemberOrderItemComponent,
    MemberOrderItemDetailComponent,
    MemberOrderItemUpdateComponent,
    MemberOrderItemDeletePopupComponent,
    MemberOrderItemDeleteDialogComponent,
    memberOrderItemRoute,
    memberOrderItemPopupRoute
} from './';

const ENTITY_STATES = [...memberOrderItemRoute, ...memberOrderItemPopupRoute];

@NgModule({
    imports: [TcaSwagSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MemberOrderItemComponent,
        MemberOrderItemDetailComponent,
        MemberOrderItemUpdateComponent,
        MemberOrderItemDeleteDialogComponent,
        MemberOrderItemDeletePopupComponent
    ],
    entryComponents: [
        MemberOrderItemComponent,
        MemberOrderItemUpdateComponent,
        MemberOrderItemDeleteDialogComponent,
        MemberOrderItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagMemberOrderItemModule {}
