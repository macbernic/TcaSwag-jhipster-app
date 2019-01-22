import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TcaSwagSharedModule } from 'app/shared';
import {
    TcaOrderComponent,
    TcaOrderDetailComponent,
    TcaOrderUpdateComponent,
    TcaOrderDeletePopupComponent,
    TcaOrderDeleteDialogComponent,
    tcaOrderRoute,
    tcaOrderPopupRoute
} from './';

const ENTITY_STATES = [...tcaOrderRoute, ...tcaOrderPopupRoute];

@NgModule({
    imports: [TcaSwagSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TcaOrderComponent,
        TcaOrderDetailComponent,
        TcaOrderUpdateComponent,
        TcaOrderDeleteDialogComponent,
        TcaOrderDeletePopupComponent
    ],
    entryComponents: [TcaOrderComponent, TcaOrderUpdateComponent, TcaOrderDeleteDialogComponent, TcaOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagTcaOrderModule {}
