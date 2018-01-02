/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt1DetailComponent } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1-detail.component';
import { SaleAmt1Service } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.service';
import { SaleAmt1 } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.model';

describe('Component Tests', () => {

    describe('SaleAmt1 Management Detail Component', () => {
        let comp: SaleAmt1DetailComponent;
        let fixture: ComponentFixture<SaleAmt1DetailComponent>;
        let service: SaleAmt1Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt1DetailComponent],
                providers: [
                    SaleAmt1Service
                ]
            })
            .overrideTemplate(SaleAmt1DetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt1DetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt1Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SaleAmt1(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.saleAmt1).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
