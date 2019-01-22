import { NgModule } from '@angular/core';

import { TcaSwagSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TcaSwagSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TcaSwagSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TcaSwagSharedCommonModule {}
