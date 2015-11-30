define([
    'jquery',
    'angular',
    'angular-couch-potato',
    'locale-zh-cn',
    'angular-animate',
    'angular-growl',
    'angularTranslate',
    'angular-ui-router',
    'services',
    'ngCookies',
    'uiBootstrap',
    'datepicker',
    'ngSanitize',
    'ui-tree',
    'mcDatePicker',
    'dragDropDirective',
    'angupoly',
    'angular-toggle-switch',
    'ngIdle',
    'uiSelect2Module',
    'angular-ui-select',
    'sortable',
    'angularFileUpload'
    ], function ($, angular, couchPotato) {

        'use strict';

        var app = angular.module('olive', [
                    'scs.couch-potato',
                    'ngLocale',
                    'ngAnimate',
                    'angular-growl',
                    'pascalprecht.translate',
                    'ui.router',
                    'olive.service',
                    'ngCookies',
                    'ui.bootstrap',
                    'mc.datepicker',
                    'dragDropDirective',
                    'ui.tree',
                    'angupoly',
                    'toggle-switch',
//                    'blueimp.fileupload',
                    'ngIdle',
                    'ui.select2',
                    'ui.select', 
                    'ngSanitize',
                    'ui.sortable',
                    'angularFileUpload'
        ]);

    couchPotato.configureApp(app);
    return app;
});