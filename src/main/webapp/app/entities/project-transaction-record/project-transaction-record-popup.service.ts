import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProjectTransactionRecord } from './project-transaction-record.model';
import { ProjectTransactionRecordService } from './project-transaction-record.service';

@Injectable()
export class ProjectTransactionRecordPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private projectTransactionRecordService: ProjectTransactionRecordService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.projectTransactionRecordService.find(id).subscribe((projectTransactionRecord) => {
                    this.ngbModalRef = this.projectTransactionRecordModalRef(component, projectTransactionRecord);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.projectTransactionRecordModalRef(component, new ProjectTransactionRecord());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    projectTransactionRecordModalRef(component: Component, projectTransactionRecord: ProjectTransactionRecord): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.projectTransactionRecord = projectTransactionRecord;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
