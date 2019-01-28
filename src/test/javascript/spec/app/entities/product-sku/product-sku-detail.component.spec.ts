/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductSkuDetailComponent } from 'app/entities/product-sku/product-sku-detail.component';
import { ProductSku } from 'app/shared/model/product-sku.model';

describe('Component Tests', () => {
    describe('ProductSku Management Detail Component', () => {
        let comp: ProductSkuDetailComponent;
        let fixture: ComponentFixture<ProductSkuDetailComponent>;
        const route = ({ data: of({ productSku: new ProductSku(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductSkuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductSkuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductSkuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productSku).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
