/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { MemberOrderItemUpdateComponent } from 'app/entities/member-order-item/member-order-item-update.component';
import { MemberOrderItemService } from 'app/entities/member-order-item/member-order-item.service';
import { MemberOrderItem } from 'app/shared/model/member-order-item.model';

describe('Component Tests', () => {
    describe('MemberOrderItem Management Update Component', () => {
        let comp: MemberOrderItemUpdateComponent;
        let fixture: ComponentFixture<MemberOrderItemUpdateComponent>;
        let service: MemberOrderItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [MemberOrderItemUpdateComponent]
            })
                .overrideTemplate(MemberOrderItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MemberOrderItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemberOrderItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MemberOrderItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.memberOrderItem = entity;
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
                    const entity = new MemberOrderItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.memberOrderItem = entity;
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
