/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TcaSwagTestModule } from '../../../test.module';
import { MemberOrderItemDeleteDialogComponent } from 'app/entities/member-order-item/member-order-item-delete-dialog.component';
import { MemberOrderItemService } from 'app/entities/member-order-item/member-order-item.service';

describe('Component Tests', () => {
    describe('MemberOrderItem Management Delete Component', () => {
        let comp: MemberOrderItemDeleteDialogComponent;
        let fixture: ComponentFixture<MemberOrderItemDeleteDialogComponent>;
        let service: MemberOrderItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [MemberOrderItemDeleteDialogComponent]
            })
                .overrideTemplate(MemberOrderItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MemberOrderItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemberOrderItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
