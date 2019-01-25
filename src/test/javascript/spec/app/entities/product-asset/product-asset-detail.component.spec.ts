/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductAssetDetailComponent } from 'app/entities/product-asset/product-asset-detail.component';
import { ProductAsset } from 'app/shared/model/product-asset.model';

describe('Component Tests', () => {
    describe('ProductAsset Management Detail Component', () => {
        let comp: ProductAssetDetailComponent;
        let fixture: ComponentFixture<ProductAssetDetailComponent>;
        const route = ({ data: of({ productAsset: new ProductAsset(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductAssetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductAssetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductAssetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productAsset).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
