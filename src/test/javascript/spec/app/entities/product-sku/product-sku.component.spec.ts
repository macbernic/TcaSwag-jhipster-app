/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductSkuComponent } from 'app/entities/product-sku/product-sku.component';
import { ProductSkuService } from 'app/entities/product-sku/product-sku.service';
import { ProductSku } from 'app/shared/model/product-sku.model';

describe('Component Tests', () => {
    describe('ProductSku Management Component', () => {
        let comp: ProductSkuComponent;
        let fixture: ComponentFixture<ProductSkuComponent>;
        let service: ProductSkuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductSkuComponent],
                providers: []
            })
                .overrideTemplate(ProductSkuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductSkuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSkuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductSku(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productSkus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
