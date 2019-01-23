/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { MemberOrderUpdateComponent } from 'app/entities/member-order/member-order-update.component';
import { MemberOrderService } from 'app/entities/member-order/member-order.service';
import { MemberOrder } from 'app/shared/model/member-order.model';

describe('Component Tests', () => {
    describe('MemberOrder Management Update Component', () => {
        let comp: MemberOrderUpdateComponent;
        let fixture: ComponentFixture<MemberOrderUpdateComponent>;
        let service: MemberOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [MemberOrderUpdateComponent]
            })
                .overrideTemplate(MemberOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MemberOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemberOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MemberOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.memberOrder = entity;
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
                    const entity = new MemberOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.memberOrder = entity;
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
