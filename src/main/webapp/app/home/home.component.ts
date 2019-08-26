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
        'Step 1@',
        'Step 2@source-connection',
        'Step 3@source-connection/new',
        'Step 4@source-connection/new',
        'Step 5@source-connection/new',
        'Step 6@source-connection/new',
        'Step 7@source-connection/new',
        'Step 8@source-connection/new',
        'Step 9@source-connection/new',
        'Step 10@source-connection/new',
        'Step 11@source-connection/new',
        'Step 12@snowflake-connection',
        'Step 13@snowflake-connection/new',
        'Step 14@snowflake-connection/new',
        'Step 15@snowflake-connection/new',
        'Step 16@snowflake-connection/new',
        'Step 17@snowflake-connection/new',
        'Step 18@snowflake-connection/new',
        'Step 19@snowflake-connection/new',
        'Step 20@snowflake-connection/new',
        'Step 21@snowflake-connection/new',
        'Step 22@snowflake-connection/new',
        'Step 23@migration-process',
        'Step 24@migration-process/new',
        'Step 25@migration-process/new',
        'Step 26@migration-process/new',
        'Step 27@migration-process/new',
        'Step 28@snow-ddl',
        'Step 29@snow-ddl/new',
        'Step 30@snow-ddl/new',
        'Step 31@snow-ddl/new',
        'Step 32@snow-ddl/new',
        'Step 33@snow-ddl/new',
        'Step 34@snow-ddl/new',
        'Step 35@snow-ddl/new',
        'Step 36@snow-ddl/new'
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
