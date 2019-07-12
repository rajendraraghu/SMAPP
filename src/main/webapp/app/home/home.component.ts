import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { JoyrideService } from 'ngx-joyride';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: Account;
  modalRef: NgbModalRef;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private readonly joyrideService: JoyrideService
  ) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
  }

  onClick() {
    this.joyrideService.startTour({
      steps: [
        'firstStep@',
        'secondStep@source-connection',
        'thirdStep@source-connection/new',
        'fourthStep@source-connection/new',
        'fifthStep@source-connection/new',
        'sixthStep@source-connection/new',
        'seventhStep@source-connection/new',
        'eigthStep@source-connection/new',
        'ninethStep@source-connection/new',
        'tengthStep@snowflake-connection',
        'elevengthStep@snowflake-connection/new',
        'twelthStep@migration-process',
        'thirteenthStep@migration-process/new'
      ],
      stepDefaultPosition: 'bottom'
    });
    this.joyrideService.startTour(
      { steps: ['firstStep', 'secondStep'] } // Your steps order
    );
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }
}
