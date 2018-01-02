/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt1Component } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.component';
import { SaleAmt1Service } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.service';
import { SaleAmt1 } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.model';

describe('Component Tests', () => {

    describe('SaleAmt1 Management Component', () => {
        let comp: SaleAmt1Component;
        let fixture: ComponentFixture<SaleAmt1Component>;
        let service: SaleAmt1Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt1Component],
                providers: [
                    SaleAmt1Service
                ]
            })
            .overrideTemplate(SaleAmt1Component, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt1Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt1Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SaleAmt1(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.saleAmt1S[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
