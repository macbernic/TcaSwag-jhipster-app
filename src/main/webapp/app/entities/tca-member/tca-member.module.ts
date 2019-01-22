import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TcaSwagSharedModule } from 'app/shared';
import { TcaSwagAdminModule } from 'app/admin/admin.module';
import {
    TcaMemberComponent,
    TcaMemberDetailComponent,
    TcaMemberUpdateComponent,
    TcaMemberDeletePopupComponent,
    TcaMemberDeleteDialogComponent,
    tcaMemberRoute,
    tcaMemberPopupRoute
} from './';

const ENTITY_STATES = [...tcaMemberRoute, ...tcaMemberPopupRoute];

@NgModule({
    imports: [TcaSwagSharedModule, TcaSwagAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TcaMemberComponent,
        TcaMemberDetailComponent,
        TcaMemberUpdateComponent,
        TcaMemberDeleteDialogComponent,
        TcaMemberDeletePopupComponent
    ],
    entryComponents: [TcaMemberComponent, TcaMemberUpdateComponent, TcaMemberDeleteDialogComponent, TcaMemberDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagTcaMemberModule {}
