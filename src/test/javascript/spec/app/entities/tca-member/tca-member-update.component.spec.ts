/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaMemberUpdateComponent } from 'app/entities/tca-member/tca-member-update.component';
import { TcaMemberService } from 'app/entities/tca-member/tca-member.service';
import { TcaMember } from 'app/shared/model/tca-member.model';

describe('Component Tests', () => {
    describe('TcaMember Management Update Component', () => {
        let comp: TcaMemberUpdateComponent;
        let fixture: ComponentFixture<TcaMemberUpdateComponent>;
        let service: TcaMemberService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaMemberUpdateComponent]
            })
                .overrideTemplate(TcaMemberUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TcaMemberUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TcaMemberService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TcaMember(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tcaMember = entity;
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
                    const entity = new TcaMember();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tcaMember = entity;
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
