/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { MemberOrderDetailComponent } from 'app/entities/member-order/member-order-detail.component';
import { MemberOrder } from 'app/shared/model/member-order.model';

describe('Component Tests', () => {
    describe('MemberOrder Management Detail Component', () => {
        let comp: MemberOrderDetailComponent;
        let fixture: ComponentFixture<MemberOrderDetailComponent>;
        const route = ({ data: of({ memberOrder: new MemberOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [MemberOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MemberOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MemberOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.memberOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
