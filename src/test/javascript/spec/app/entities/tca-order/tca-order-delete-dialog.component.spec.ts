/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TcaSwagTestModule } from '../../../test.module';
import { TcaOrderDeleteDialogComponent } from 'app/entities/tca-order/tca-order-delete-dialog.component';
import { TcaOrderService } from 'app/entities/tca-order/tca-order.service';

describe('Component Tests', () => {
    describe('TcaOrder Management Delete Component', () => {
        let comp: TcaOrderDeleteDialogComponent;
        let fixture: ComponentFixture<TcaOrderDeleteDialogComponent>;
        let service: TcaOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [TcaOrderDeleteDialogComponent]
            })
                .overrideTemplate(TcaOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TcaOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TcaOrderService);
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
