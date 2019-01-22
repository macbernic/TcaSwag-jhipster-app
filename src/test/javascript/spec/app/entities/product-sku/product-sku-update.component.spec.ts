/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductSkuUpdateComponent } from 'app/entities/product-sku/product-sku-update.component';
import { ProductSkuService } from 'app/entities/product-sku/product-sku.service';
import { ProductSku } from 'app/shared/model/product-sku.model';

describe('Component Tests', () => {
    describe('ProductSku Management Update Component', () => {
        let comp: ProductSkuUpdateComponent;
        let fixture: ComponentFixture<ProductSkuUpdateComponent>;
        let service: ProductSkuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductSkuUpdateComponent]
            })
                .overrideTemplate(ProductSkuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductSkuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSkuService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductSku(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productSku = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductSku();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productSku = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
