/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaOrderDetailComponent } from 'app/entities/tca-order/tca-order-detail.component';
import { TcaOrder } from 'app/shared/model/tca-order.model';

describe('Component Tests', () => {
    describe('TcaOrder Management Detail Component', () => {
        let comp: TcaOrderDetailComponent;
        let fixture: ComponentFixture<TcaOrderDetailComponent>;
        const route = ({ data: of({ tcaOrder: new TcaOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TcaOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TcaOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tcaOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
