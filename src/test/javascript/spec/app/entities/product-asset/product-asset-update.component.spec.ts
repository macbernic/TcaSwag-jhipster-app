/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductAssetUpdateComponent } from 'app/entities/product-asset/product-asset-update.component';
import { ProductAssetService } from 'app/entities/product-asset/product-asset.service';
import { ProductAsset } from 'app/shared/model/product-asset.model';

describe('Component Tests', () => {
    describe('ProductAsset Management Update Component', () => {
        let comp: ProductAssetUpdateComponent;
        let fixture: ComponentFixture<ProductAssetUpdateComponent>;
        let service: ProductAssetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductAssetUpdateComponent]
            })
                .overrideTemplate(ProductAssetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductAssetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductAssetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductAsset(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productAsset = entity;
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
                    const entity = new ProductAsset();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productAsset = entity;
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
