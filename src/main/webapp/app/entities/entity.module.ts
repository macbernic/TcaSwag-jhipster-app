import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TcaSwagTcaOrderModule } from './tca-order/tca-order.module';
import { TcaSwagProductModule } from './product/product.module';
import { TcaSwagProductSkuModule } from './product-sku/product-sku.module';
import { TcaSwagTcaMemberModule } from './tca-member/tca-member.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TcaSwagTcaOrderModule,
        TcaSwagProductModule,
        TcaSwagProductSkuModule,
        TcaSwagTcaMemberModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TcaSwagEntityModule {}
