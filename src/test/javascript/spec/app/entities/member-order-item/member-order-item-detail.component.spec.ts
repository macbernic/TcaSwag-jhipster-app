/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TcaSwagTestModule } from '../../../test.module';
import { MemberOrderItemDetailComponent } from 'app/entities/member-order-item/member-order-item-detail.component';
import { MemberOrderItem } from 'app/shared/model/member-order-item.model';

describe('Component Tests', () => {
    describe('MemberOrderItem Management Detail Component', () => {
        let comp: MemberOrderItemDetailComponent;
        let fixture: ComponentFixture<MemberOrderItemDetailComponent>;
        const route = ({ data: of({ memberOrderItem: new MemberOrderItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [MemberOrderItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MemberOrderItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MemberOrderItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.memberOrderItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
