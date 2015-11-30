define(['angular',

    'services/lemon.service.code',
    'services/olive.service.security',
    'services/lemon.service.resource',
    'services/lemon.service.review',
    'services/lemon.service.listen',
    'services/lemon.service.mynotes',
    'services/lemon.service.myactives',
    'services/lemon.service.schedule',
    'services/lemon.service.course',
    'services/lemon.service.live',
    'services/lemon.service.tree',
    'services/lemon.service.anchorScroll',
    'services/olive.service.message',
    'services/olive.service.video'
], function (angular) {
    angular.module('olive.service', [
        'lemon.service.code',
        'olive.service.security',
        'lemon.service.resource',
        'lemon.service.review',
        'lemon.service.listen',
        'lemon.service.mynotes',
        'lemon.service.myactives',
        'lemon.service.schedule',
        'lemon.service.course',
        'lemon.service.live',
        'lemon.service.tree',
        'lemon.service.AnchorScroll',
        'olive.service.message',
        'olive.service.video'
    ]);
});