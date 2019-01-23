import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TcaSwagProductModule } from './product/product.module';
import { TcaSwagProductAssetModule } from './product-asset/product-asset.module';
import { TcaSwagMemberOrderModule } from './member-order/member-order.module';
import { TcaSwagMemberOrderItemModule } from './member-order-item/member-order-item.module';
import { TcaSwagMemberModule } from './member/member.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TcaSwagProductModule,
        TcaSwagProductAssetModule,
        TcaSwagMemberOrderModule,
        TcaSwagMemberOrderItemModule,
        TcaSwagMemberModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagEntityModule {}
