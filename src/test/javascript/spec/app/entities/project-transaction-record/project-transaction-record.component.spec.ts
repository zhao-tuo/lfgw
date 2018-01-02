/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { ProjectTransactionRecordComponent } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.component';
import { ProjectTransactionRecordService } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.service';
import { ProjectTransactionRecord } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.model';

describe('Component Tests', () => {

    describe('ProjectTransactionRecord Management Component', () => {
        let comp: ProjectTransactionRecordComponent;
        let fixture: ComponentFixture<ProjectTransactionRecordComponent>;
        let service: ProjectTransactionRecordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectTransactionRecordComponent],
                providers: [
                    ProjectTransactionRecordService
                ]
            })
            .overrideTemplate(ProjectTransactionRecordComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectTransactionRecordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectTransactionRecordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ProjectTransactionRecord(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.projectTransactionRecords[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
