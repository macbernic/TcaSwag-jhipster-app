/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaOrderUpdateComponent } from 'app/entities/tca-order/tca-order-update.component';
import { TcaOrderService } from 'app/entities/tca-order/tca-order.service';
import { TcaOrder } from 'app/shared/model/tca-order.model';

describe('Component Tests', () => {
    describe('TcaOrder Management Update Component', () => {
        let comp: TcaOrderUpdateComponent;
        let fixture: ComponentFixture<TcaOrderUpdateComponent>;
        let service: TcaOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaOrderUpdateComponent]
            })
                .overrideTemplate(TcaOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TcaOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TcaOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TcaOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tcaOrder = entity;
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
                    const entity = new TcaOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tcaOrder = entity;
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
