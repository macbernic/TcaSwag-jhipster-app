/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaMemberDetailComponent } from 'app/entities/tca-member/tca-member-detail.component';
import { TcaMember } from 'app/shared/model/tca-member.model';

describe('Component Tests', () => {
    describe('TcaMember Management Detail Component', () => {
        let comp: TcaMemberDetailComponent;
        let fixture: ComponentFixture<TcaMemberDetailComponent>;
        const route = ({ data: of({ tcaMember: new TcaMember(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaMemberDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TcaMemberDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TcaMemberDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tcaMember).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
