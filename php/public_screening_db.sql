-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Jul 2014 um 17:15
-- Server Version: 5.6.16
-- PHP-Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `public_screening_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `images`
--

CREATE TABLE IF NOT EXISTS `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(50) NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Daten für Tabelle `images`
--

INSERT INTO `images` (`id`, `path`, `location_id`) VALUES
(16, 'img/atrium.jpg', 51),
(17, 'img/reservebank.jpg', 52),
(18, 'img/leibnizallee.jpg', 53),
(19, 'img/m18.jpg', 54),
(20, 'img/weimarhalle.jpg', 55),
(21, 'img/eurocafe.jpg', 56),
(22, 'img/vereinheimJena.JPG', 57);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=67 ;

--
-- Daten für Tabelle `locations`
--

INSERT INTO `locations` (`id`, `lat`, `lon`, `name`, `description`) VALUES
(51, 50.985197, 11.329436, 'Atrium', 'Das Atrium wurde 2005 als Einkaufszentrum eröffnet und zeigt während der WM 2014 alle Spieler der Deutschen Nationalmannschaft sowie die Halbfinale und das Finale. Wer neben dem Fußball noch Unmengen Geld ausgeben will ist hier definitiv richtig.'),
(52, 50.974119, 11.327479, 'Reservebank', 'Die Reservebank ist eine kleine Kneipe in Weimar, in der prinzipiell immer Fußball läuft, wenn Fußball kommt. Also auch, wenn keine Deutschen speile sind. Und auch wenn keine WM ist. Hier findet man eher Leute, die sich wirklich für Fußball interessieren als anderswo und besonders teuer ist das Bier auch nicht.'),
(53, 50.979213, 11.338137, 'Wohnheim Leibnizallee', 'Das Wohnheim Leibnizallee ist eine Institution des Studentenwerk Thüringen. Hier wohnen viele Menschen, und einige davon schmeißen gerne mal den Grill an, wenn Deutschland kickt. Die Leute die hier vorbeikommen schauen auch den DFB-Pokal oder die Championsleauge, sind aber definitiv Möchtegerne-Fans.'),
(54, 50.974987, 11.329554, 'M18 - Haus der Studierenden', 'Die M18 ist das ganze Jahr über voll mit Leuten, die nur studieren weil sie gerne das billige Mensaessen genießen wollen und studieren cool ist. Fußball spielt hier nur alle zwei Jahre eine Rolle, dann gibt es aber eine größere Leinwand und viel Gesöff. Wer mehr am Event als am Spiel interessiert ist, ist hier sicher richtig und kann sich im Normalfall auf irgendeine Form von Afterparty verlassen.'),
(55, 50.983227, 11.325063, 'Neue Weimarhalle', 'Die neue Weimarhalle ist definitiv der Ort mit der größten Leinwand in Weimar. Und auch der Ort, an dem man eher den Gutverdiener zum Screening trifft. Dafür gibt es akzeptables bis gutes essen und vermutlich auch Schampus. Wer erleben wie genau vollständig kapitalisierter Fußball aussieht, ist hier richtig.'),
(56, 50.983371, 11.325798, 'Eurocafe Weimar', 'Das Eurocafe ist das kleinste, billigste Loch, in das eine Menschengruppe potentiell reinpasst. Wer Bier will, dass so billig ist, dass es nur verwässert sein KANN und Lust hat, sich mit Leuten zu unterhalten, die den Ball in jeder Spielsituation mindestens zweimal sehen, wird hier seinen Spaß haben. Das einzige was diesem Laden zur Kategorisierung zum absoluten Höllenloch fehlt ist die Aufhebung des Rauchverbots.'),
(57, 50.91675, 11.58305, 'Vereinsheim des FCC', 'Direkt am Ernst-Abbe-Sportfeld in Jena gelegen ist dieser Ort eine Möglichkeit mit Leuten Fußball zu schauen, die schon Fußball geschaut haben, als Bernd Schneider noch in der U16 beim FCC gespielt hat. Jeder ist hier willkommen, solange er keinen Schal vom RWE um den Hals hat.');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=111 ;

--
-- Daten für Tabelle `tags`
--

INSERT INTO `tags` (`id`, `name`, `location_id`) VALUES
(63, 'entry fee', 55),
(64, 'food', 55),
(65, 'alcohol', 55),
(66, 'huge screen', 55),
(67, 'no smoking', 55),
(68, 'free', 56),
(69, 'hellhole', 56),
(70, 'alcohol', 56),
(71, 'alcoholics', 56),
(72, 'cheap beer', 56),
(73, 'no smoking', 56),
(74, 'soccer experts', 57),
(75, 'free', 57),
(76, 'alcohol', 57),
(77, 'fries', 57),
(78, 'smoking', 57),
(79, 'free', 54),
(80, 'student hipsters', 54),
(81, 'club mate', 54),
(82, 'beer', 54),
(83, 'smoking', 54),
(84, 'big screen', 54),
(85, 'potentially drugs', 54),
(86, 'free', 53),
(87, 'private', 53),
(88, 'byob', 53),
(89, 'byof', 53),
(90, 'smoking', 53),
(91, 'free', 52),
(92, 'beer', 52),
(93, 'alcohol', 52),
(94, 'food', 52),
(95, 'smoking', 52),
(96, 'free', 51),
(97, 'food', 51),
(98, 'alcohol', 51),
(99, 'commercial', 51),
(100, 'no smoking', 51);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
