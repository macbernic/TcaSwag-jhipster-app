/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TcaSwagTestModule } from '../../../test.module';
import { ProductSkuDeleteDialogComponent } from 'app/entities/product-sku/product-sku-delete-dialog.component';
import { ProductSkuService } from 'app/entities/product-sku/product-sku.service';

describe('Component Tests', () => {
    describe('ProductSku Management Delete Component', () => {
        let comp: ProductSkuDeleteDialogComponent;
        let fixture: ComponentFixture<ProductSkuDeleteDialogComponent>;
        let service: ProductSkuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TcaSwagTestModule],
                declarations: [ProductSkuDeleteDialogComponent]
            })
                .overrideTemplate(ProductSkuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductSkuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSkuService);
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
