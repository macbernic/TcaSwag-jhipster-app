/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaMemberComponent } from 'app/entities/tca-member/tca-member.component';
import { TcaMemberService } from 'app/entities/tca-member/tca-member.service';
import { TcaMember } from 'app/shared/model/tca-member.model';

describe('Component Tests', () => {
    describe('TcaMember Management Component', () => {
        let comp: TcaMemberComponent;
        let fixture: ComponentFixture<TcaMemberComponent>;
        let service: TcaMemberService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaMemberComponent],
                providers: []
            })
                .overrideTemplate(TcaMemberComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TcaMemberComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TcaMemberService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TcaMember(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tcaMembers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
