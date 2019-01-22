import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TcaSwagSharedModule } from 'app/shared';
import {
    ProductSkuComponent,
    ProductSkuDetailComponent,
    ProductSkuUpdateComponent,
    ProductSkuDeletePopupComponent,
    ProductSkuDeleteDialogComponent,
    productSkuRoute,
    productSkuPopupRoute
} from './';

const ENTITY_STATES = [...productSkuRoute, ...productSkuPopupRoute];

@NgModule({
    imports: [TcaSwagSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductSkuComponent,
        ProductSkuDetailComponent,
        ProductSkuUpdateComponent,
        ProductSkuDeleteDialogComponent,
        ProductSkuDeletePopupComponent
    ],
    entryComponents: [ProductSkuComponent, ProductSkuUpdateComponent, ProductSkuDeleteDialogComponent, ProductSkuDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagProductSkuModule {}
