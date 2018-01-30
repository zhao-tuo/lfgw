import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MenuData } from './menu-data.model';
import { MenuDataPopupService } from './menu-data-popup.service';
import { MenuDataService } from './menu-data.service';

@Component({
    selector: 'jhi-menu-data-delete-dialog',
    templateUrl: './menu-data-delete-dialog.component.html'
})
export class MenuDataDeleteDialogComponent {

    menuData: MenuData;

    constructor(
        private menuDataService: MenuDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.menuDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'menuDataListModification',
                content: 'Deleted an menuData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-menu-data-delete-popup',
    template: ''
})
export class MenuDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private menuDataPopupService: MenuDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.menuDataPopupService
                .open(MenuDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
