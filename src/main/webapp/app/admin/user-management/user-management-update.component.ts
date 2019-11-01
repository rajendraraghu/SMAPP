import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, User, UserService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
  user: User;
  // languages: any[];
  authorities: any[];
  isSaving: boolean;
  confirmPassword: String;
  password: String;
  doNotMatch: string;
  error: string;
  success: string;

  editForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    id: [null],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    // activated: [true],
    // langKey: [],
    authorities: ['', [Validators.required]]
    // password: [
    //   '',
    //   [
    //     Validators.required,
    //     Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-zd$@$!%*?&].{8,}'),
    //     Validators.minLength(8),
    //     Validators.maxLength(12)
    //   ]
    // ],
    // confirmPassword: ['', [Validators.required]]
  });
  passwordService: any;

  constructor(
    private languageHelper: JhiLanguageHelper,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ user }) => {
      this.user = user.body ? user.body : user;
      this.updateForm(this.user);
    });
    this.authorities = [];
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      authorities: user.authorities
      // password: user.password
    });
  }

  previousState() {
    window.history.back();
  }

  // changePassword() {
  //   const password = this.editForm.get(['password']).value;
  //   if (password !== this.editForm.get(['confirmPassword']).value) {
  //     const smsg = 'global.messages.error.notmatching';
  //     this.jhiAlertService.error(smsg);
  //     this.doNotMatch = 'ERROR';
  //   } else {
  //     this.doNotMatch = null;
  //     // const smsg = 'global.messages.error.matching';
  //     // this.jhiAlertService.success(smsg);
  //     this.save();
  //   }
  // }

  save() {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== null) {
      this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    } else {
      this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }
  }

  private updateUser(user: User): void {
    user.authorities = [];
    user.login = this.editForm.get(['login']).value;
    user.firstName = this.editForm.get(['firstName']).value;
    user.lastName = this.editForm.get(['lastName']).value;
    user.email = this.editForm.get(['email']).value;
    // user.activated = this.editForm.get(['activated']).value;
    // user.langKey = this.editForm.get(['langKey']).value;
    user.authorities.push(this.editForm.get(['authorities']).value);
    // user.password = this.editForm.get(['password']).value;
  }

  private onSaveSuccess(result) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
